/** 
 * Copyright (c) 2009, Regents of the University of Colorado 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of the University of Colorado at Boulder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE. 
 */
package org.cleartk.examples.pos;

import java.io.File;
import java.util.Arrays;

import org.apache.uima.collection.CollectionReader;
import org.cleartk.syntax.opennlp.SentenceAnnotator;
import org.cleartk.token.stem.snowball.DefaultSnowballStemmer;
import org.cleartk.token.tokenizer.TokenAnnotator;
import org.cleartk.util.ae.UriToDocumentTextAnnotator;
import org.cleartk.util.cr.UriCollectionReader;
import org.uimafit.factory.AggregateBuilder;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.pipeline.SimplePipeline;

/**
 * <br>
 * Copyright (c) 2009, Regents of the University of Colorado <br>
 * All rights reserved.
 * 
 * @author Philip Ogren
 * 
 *         For examples of using the ExamplePOSAnnotationHandler using different classifiers, please
 *         see org.cleartk.example.pos.ExamplePosClassifierTest
 */

public class RunExamplePOSAnnotator {

  public static void main(String[] args) throws Exception {

    // Create reader
    CollectionReader reader = UriCollectionReader.getCollectionReaderFromFiles(Arrays.asList(new File(
        "src/main/resources/data/sample/2008_Sichuan_earthquake.txt")));

    // Create aggregate engine
    AggregateBuilder builder = new AggregateBuilder();
    builder.add(UriToDocumentTextAnnotator.getDescription());
    builder.add(SentenceAnnotator.getDescription());
    builder.add(TokenAnnotator.getDescription());
    builder.add(DefaultSnowballStemmer.getDescription("English"));
    builder.add(ExamplePOSAnnotator.getClassifierDescription(ExamplePOSAnnotator.DEFAULT_MODEL));
    builder.add(AnalysisEngineFactory.createPrimitiveDescription(
        ExamplePOSPlainTextWriter.class,
        ExamplePOSPlainTextWriter.PARAM_OUTPUT_DIRECTORY_NAME,
        ExamplePOSPlainTextWriter.DEFAULT_OUTPUT_DIRECTORY));

    // Run pipeline
    SimplePipeline.runPipeline(reader, builder.createAggregateDescription());

    System.out.println("Please look at the file generated by this program: "
        + ExamplePOSPlainTextWriter.DEFAULT_OUTPUT_DIRECTORY + "/2008_Sichuan_earthquake.txt.pos");
  }
}